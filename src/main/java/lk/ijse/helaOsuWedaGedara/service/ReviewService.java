package lk.ijse.helaOsuWedaGedara.service;

import lk.ijse.helaOsuWedaGedara.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO addReview(ReviewDTO dto);
    List<ReviewDTO> getReviewsByDoctor(Long doctorId);
    List<ReviewDTO> getAllReviews();
}
