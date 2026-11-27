package pl.lab.roombooking.service;

import org.springframework.stereotype.Service;
import pl.lab.roombooking.dto.CreateRoomRequest;
import pl.lab.roombooking.dto.RoomResponse;
import pl.lab.roombooking.dto.UpdateRoomRequest;
import pl.lab.roombooking.model.Room;
import pl.lab.roombooking.repository.InMemoryRoomRepository;

import java.util.List;
import java.util.Optional;

/**
 * Warstwa logiki biznesowej (Service).
 * Pośredniczy między kontrolerem HTTP a warstwą dostępu do danych (Repository),
 * mapując encje domenowe na obiekty DTO.
 */
@Service
public class RoomService {

    private final InMemoryRoomRepository roomRepository;

    // Wstrzykiwanie zależności (Dependency Injection) przez konstruktor
    public RoomService(InMemoryRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomResponse::from)
                .toList();
    }

    // TODO (Zadanie 1): Pobierz pojedynczą salę na podstawie ID i zmapuj na Optional<RoomResponse>.
    public Optional<RoomResponse> getRoomById(Long id) {
        return roomRepository.findById(id).map(RoomResponse::from);
    }

    // TODO (Zadanie 2): Utwórz nowy obiekt Room na podstawie CreateRoomRequest (available = true),
    // zapisz go w repozytorium i zwróć RoomResponse.
    public RoomResponse createRoom(CreateRoomRequest request) {
        Room room = new Room(
                null,
                request.name(),
                request.building(),
                request.floor(),
                request.capacity(),
                request.hasProjector(),
                true
        );
        return RoomResponse.from(roomRepository.save(room));
    }

    // TODO (Zadanie 3): Zaktualizuj istniejącą salę o podanym ID danymi z UpdateRoomRequest.
    // Jeśli sala nie istnieje, zwróć Optional.empty().
    public Optional<RoomResponse> updateRoom(Long id, UpdateRoomRequest request) {
        return roomRepository.findById(id).map(room -> {
            room.setName(request.name());
            room.setBuilding(request.building());
            room.setFloor(request.floor());
            room.setCapacity(request.capacity());
            room.setHasProjector(request.hasProjector());
            room.setAvailable(request.available());
            return RoomResponse.from(roomRepository.save(room));
        });
    }

    // TODO (Zadanie 4): Usuń salę o podanym ID, delegując operację do repozytorium.
    public boolean deleteRoom(Long id) {
        return roomRepository.deleteById(id);
    }

    // TODO (Zadanie 5): Rozbuduj pobieranie listy sal o opcjonalne filtrowanie
    // po parametrach minCapacity oraz hasProjector (gdy parametr == null, nie filtruj po nim).
    public List<RoomResponse> searchRooms(Integer minCapacity, Boolean hasProjector) {
        return getAllRooms();
    }
}
