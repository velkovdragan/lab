package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.InstructorRepository;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final InstructorRepository instructorRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository,
                               InstructorRepository instructorRepository) {
        this.workshopRepository = workshopRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public List<Workshop> listAll() {
        return workshopRepository.findAll();
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        return workshopRepository.search(text, maxPrice);
    }

    @Override
    public Optional<Workshop> findById(Long id) {
        return workshopRepository.findById(id);
    }

    @Override
    public Workshop create(String title, String category, Double price, Integer capacity, Long instructorId) {
        validate(title, category, price, capacity);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        Workshop workshop = new Workshop(title, category, price, capacity, instructor);
        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop update(Long id, String title, String category, Double price, Integer capacity, Long instructorId) {
        validate(title, category, price, capacity);

        Workshop workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workshop not found"));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        workshop.setTitle(title);
        workshop.setCategory(category);
        workshop.setPrice(price);
        workshop.setCapacity(capacity);
        workshop.setInstructor(instructor);

        return workshopRepository.save(workshop);
    }

    @Override
    public void deleteById(Long id) {
        workshopRepository.deleteById(id);
    }

    private void validate(String title, String category, Double price, Integer capacity) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category must not be empty");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be zero or positive");
        }
        if (capacity == null || capacity < 0) {
            throw new IllegalArgumentException("Capacity must be zero or positive");
        }
    }
}
