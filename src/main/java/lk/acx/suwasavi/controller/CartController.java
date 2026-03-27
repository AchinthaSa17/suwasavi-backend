package lk.acx.suwasavi.controller;

import lk.acx.suwasavi.entity.CartItem;
import lk.acx.suwasavi.entity.Booking;
import lk.acx.suwasavi.repository.CartItemRepository;
import lk.acx.suwasavi.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartItemRepository cartRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItem item) {
        // Optional: Check if the item is already in the cart for this user
        if (cartRepository.existsByFirebaseUidAndServiceId(item.getFirebaseUid(), item.getServiceId())) {
            return ResponseEntity.status(409).body("Item already in cart");
        }

        CartItem savedItem = cartRepository.save(item);
        return ResponseEntity.ok(savedItem);
    }

    @PostMapping("/checkout/{uid}")
    @Transactional
    public ResponseEntity<Void> checkout(
            @PathVariable String uid,
            @RequestParam String date,
            @RequestParam String address,
            @RequestParam String timeSlot) {

        List<CartItem> items = cartRepository.findByFirebaseUid(uid);

        if (items.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        for (CartItem item : items) {
            Booking booking = new Booking();
            booking.setFirebaseUid(item.getFirebaseUid());
            booking.setServiceId(item.getServiceId());
            booking.setServiceName(item.getServiceName());
            booking.setPrice(item.getPrice());
            booking.setStatus("PAID");
            booking.setBookingDate(date);   // User selected date
            booking.setAddress(address);     // User entered address
            booking.setTimeSlot(timeSlot);   // User selected slot

            bookingRepository.save(booking);
        }

        cartRepository.deleteAll(items);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{uid}")
    public List<CartItem> getCart(@PathVariable String uid) {
        return cartRepository.findByFirebaseUid(uid);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/clear/{uid}")
    @Transactional
    public ResponseEntity<Void> clearCart(@PathVariable String uid) {
        // It is more efficient to use the custom repository method if you have it
        cartRepository.deleteByFirebaseUid(uid);
        return ResponseEntity.ok().build();
    }
}