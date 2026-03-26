package lk.acx.suwasavi.repository;

import lk.acx.suwasavi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Retrieves all items in the cart for a specific user.
     * Used for displaying the cart on the Android app.
     */
    List<CartItem> findByFirebaseUid(String firebaseUid);

    /**
     * Deletes all cart items for a specific user.
     * Typically called after a successful checkout/booking.
     */
    void deleteByFirebaseUid(String firebaseUid);

    /**
     * Check if a specific service is already in the user's cart.
     */
    boolean existsByFirebaseUidAndServiceId(String firebaseUid, Long serviceId);
}