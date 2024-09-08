package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.model.google.Photo;
import com.lioneats.lioneats_backend.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PhotoServiceTest {

    @Autowired
    private PhotoRepository photoRepository;

    @Test
    @Transactional
    public void testSavePhoto() {
        Photo photo = new Photo();
        photo.setImageUrl("http://example.com/photo.jpg");
        photo.setHeight(600);
        photo.setWidth(800);
        photo.setShop(new Shop());

        Photo savedPhoto = photoRepository.save(photo);

        Optional<Photo> retrievedPhoto = photoRepository.findById(savedPhoto.getId());
        assertThat(retrievedPhoto.isPresent()).isTrue();
        assertThat(retrievedPhoto.get().getId()).isEqualTo(savedPhoto.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Photo photo = new Photo();
        photo.setImageUrl("http://example.com/photo.jpg");
        photo.setHeight(600);
        photo.setWidth(800);
        photo.setShop(new Shop());

        Photo savedPhoto = photoRepository.save(photo);

        Optional<Photo> foundPhoto = photoRepository.findById(savedPhoto.getId());

        assertThat(foundPhoto.isPresent()).isTrue();
        assertThat(foundPhoto.get().getId()).isEqualTo(savedPhoto.getId());
    }
}

