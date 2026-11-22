package pl.lab.roombooking.controller;
import java.net.URI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lab.roombooking.dto.CreateRoomRequest;
import pl.lab.roombooking.dto.RoomResponse;
import pl.lab.roombooking.dto.UpdateRoomRequest;
import pl.lab.roombooking.service.RoomService;

import java.util.List;

/**
 * Kontroler REST obsługujący zasób /api/rooms.
 * Odpowiada wyłącznie za warstwę HTTP: odczyt parametrów żądania, wywołanie serwisu
 * oraz dobór odpowiedniego kodu statusu odpowiedzi (200, 201, 204, 400, 404).
 */
@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms", description = "Zarządzanie salami konferencyjnymi i dydaktycznymi")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "Pobiera listę wszystkich sal (z opcjonalnym filtrowaniem)")
    public ResponseEntity<List<RoomResponse>> getAllRooms(
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Boolean hasProjector
    ) {
        List<RoomResponse> rooms = roomService.searchRooms(minCapacity, hasProjector);
        return ResponseEntity.ok(rooms);
    }

    // TODO (Zadanie 1): Dodaj endpoint GET /api/rooms/{id}
    // - Zwróć 200 OK z obiektem RoomResponse, jeśli sala istnieje.
    // - Zwróć 404 Not Found, jeśli sala o podanym ID nie istnieje.
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO (Zadanie 2): Dodaj endpoint POST /api/rooms
    // - Odbierz @RequestBody CreateRoomRequest.
    // - Zwróć 201 Created wraz z nagłówkiem Location (/api/rooms/{id}) oraz ciałem RoomResponse.
    // - (Zadanie 6): Zwróć 400 Bad Request, gdy name jest puste lub capacity <= 0.
    @PostMapping
    @Operation(summary = "Tworzy nową salę")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody CreateRoomRequest request) {
        RoomResponse created = roomService.createRoom(request);
        URI location = URI.create("/api/rooms/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    // TODO (Zadanie 3): Dodaj endpoint PUT /api/rooms/{id}
    // - Odbierz @PathVariable Long id oraz @RequestBody UpdateRoomRequest.
    // - Zwróć 200 OK po aktualizacji lub 404 Not Found, jeśli sala nie istnieje.

    // TODO (Zadanie 4): Dodaj endpoint DELETE /api/rooms/{id}
    // - Zwróć 204 No Content po usunięciu lub 404 Not Found, jeśli sala nie istniała.
}
