package lk.ijse.helaOsuWedaGedara.controller;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import lk.ijse.helaOsuWedaGedara.dto.ReviewDTO;
import lk.ijse.helaOsuWedaGedara.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse addReview(@RequestBody ReviewDTO dto) {
        ReviewDTO saved = reviewService.addReview(dto);
        return new CommonResponse(OPERATION_SUCCESS, saved, "Review submitted successfully!");
    }

    @GetMapping(value = "/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getReviewsByDoctor(@PathVariable Long doctorId) {
        List<ReviewDTO> list = reviewService.getReviewsByDoctor(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllReviews() {
        List<ReviewDTO> list = reviewService.getAllReviews();
        return new CommonResponse(OPERATION_SUCCESS, list, SUCCESS_MESSAGE);
    }
}
