package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LoadDefaultItemsService {

    private static final Long ID_AVATAR_DEFAULT = 1000000000L;
    private static final Long ID_BANNER_DEFAULT = 1000000008L;

    @Autowired
    private GetItemByIdService getItemByIdService;

    public List<Item> load() {
        List<Item> defaultItems = new ArrayList<>();
        Item defaultAvatar = getItemByIdService.get(ID_AVATAR_DEFAULT);
        Item defaultBanner = getItemByIdService.get(ID_BANNER_DEFAULT);

        defaultItems.add(defaultAvatar);
        defaultItems.add(defaultBanner);

        return defaultItems;
    }
}
