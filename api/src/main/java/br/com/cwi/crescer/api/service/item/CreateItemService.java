package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.controller.dtos.request.item.CreateItemRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.mapper.ItemMapper;
import br.com.cwi.crescer.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class CreateItemService {

    private static final String MESSAGE_ERROR_CREATE_ITEM = "Erro ao tentar criar item";
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    public void create(CreateItemRequest createItemRequest, HttpServletRequest request) throws IOException {
        String hashUniqueImage = UUID.randomUUID().toString().replaceAll("[^a-zA-Z]+", "");
        String imageName = hashUniqueImage + createItemRequest.getImage().getOriginalFilename();
        Path uploadsDirPath = Path.of("src", "main", "resources", "static").toAbsolutePath();
        String newImageName = imageName.replaceAll("[^\\w\\s.]+", "");
        Path imagePath = uploadsDirPath.resolve(newImageName);

        try {
            createItemRequest.getImage().transferTo(imagePath);

            String url = "http://localhost:8080" + request.getContextPath() + "/static/" + newImageName;
            Item item = itemMapper.toEntity(createItemRequest);
            item.setImageUrl(url);
            itemRepository.save(item);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_CREATE_ITEM);
        }
    }
}
