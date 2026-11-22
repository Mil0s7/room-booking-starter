package pl.lab.roombooking.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pl.lab.roombooking.model.Room;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repozytorium przechowujące sale w pamięci RAM.
 * Używamy ConcurrentHashMap oraz AtomicLong, ponieważ serwer Tomcat obsługuje
 * wiele równoległych żądań HTTP w osobnych wątkach.
 */
@Repository
public class InMemoryRoomRepository {

    private final Map<Long, Room> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @PostConstruct
    void initSampleData() {
        save(new Room(null, "Sala A101", "Budynek A", 1, 24, true, true));
        save(new Room(null, "Aula B204", "Budynek B", 2, 120, true, true));
        save(new Room(null, "Laboratorium C012", "Budynek C", 0, 16, false, false));
    }

    public List<Room> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Room::getId))
                .toList();
    }

    public Room save(Room room) {
        if (room.getId() == null) {
            room.setId(sequence.incrementAndGet());
        }
        storage.put(room.getId(), room);
        return room;
    }

    // TODO (Zadanie 1): Zaimplementuj wyszukiwanie sali po identyfikatorze.
    // Wskazówka: Optional.ofNullable(storage.get(id))
    public Optional<Room> findById(Long id) {
    //    throw new UnsupportedOperationException("TODO: Zaimplementuj metodę findById w Zadaniu 1");
        return Optional.ofNullable(storage.get(id));
    }

    // TODO (Zadanie 4): Zaimplementuj usuwanie sali po identyfikatorze.
    // Metoda powinna zwrócić true, jeśli sala istniała i została usunięta, lub false w przeciwnym razie.
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException("TODO: Zaimplementuj metodę deleteById w Zadaniu 4");
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    public List<Room> findAllUnsorted() {
        return new ArrayList<>(storage.values());
    }
}
