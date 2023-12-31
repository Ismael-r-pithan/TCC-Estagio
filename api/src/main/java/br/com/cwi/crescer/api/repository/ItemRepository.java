package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.name = :search OR i.description = :search")
    Page<Item> findByNameOrDescription(Pageable pageable, String search);

}
