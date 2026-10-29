package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryWorkshopRepository implements WorkshopRepository {

    @Override
    public List<Workshop> findAll() {
        return DataHolder.workshops;
    }

    @Override
    public Optional<Workshop> findByTitle(String title) {
        return DataHolder.workshops.stream().filter(m -> m.getTitle().equals(title)).findFirst();
    }


    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        return DataHolder.workshops.stream()
                .filter(m ->
                        (text == null || text.isEmpty() ||
                                m.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                                m.getCategory().toLowerCase().contains(text.toLowerCase()))
                                &&
                                (maxPrice == null || m.getPrice() <= maxPrice)
                )
                .toList();
    }
}
