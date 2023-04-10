package rentcar.rentcar.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.Image;
import rentcar.rentcar.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Upload a file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhoto(@RequestPart("file") MultipartFile file) throws IOException, IOException {
        imageService.savePhoto(file);
        return ResponseEntity.ok().build();
    }
}

