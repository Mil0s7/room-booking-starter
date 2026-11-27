package pl.lab.roombooking.dto;

/**
 * Obiekt DTO odbierany w ciele żądania PATCH /api/rooms/{id}/availability.
 */
public record UpdateAvailabilityRequest(boolean available) {
}
