package com.urbansalon;

import com.urbansalon.modal.Salon;
import com.urbansalon.repository.SalonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class SalonUpdateTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SalonRepository salons;

    @Test
    void patchPreservesOmittedFieldsAndOwnership() throws Exception {
        Salon salon = createSalon(1L);

        mvc.perform(patch("/api/salons/{id}", salon.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"city":"Chiang Mai","ownerId":2}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Chiang Mai"))
                .andExpect(jsonPath("$.name").value("UrbanSalon"))
                .andExpect(jsonPath("$.ownerId").value(1));
    }

    @Test
    void patchRejectsOtherOwnersAndMissingSalons() throws Exception {
        Salon salon = createSalon(2L);

        mvc.perform(patch("/api/salons/{id}", salon.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"city":"Chiang Mai","ownerId":1}
                        """))
                .andExpect(status().isForbidden());

        mvc.perform(patch("/api/salons/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound());
    }

    private Salon createSalon(Long ownerId) {
        Salon salon = new Salon();
        salon.setName("UrbanSalon");
        salon.setCity("Bangkok");
        salon.setAddress("10 Main Road");
        salon.setPhoneNumber("020000000");
        salon.setEmail("salon@example.com");
        salon.setOpenTime(LocalTime.of(9, 0));
        salon.setCloseTime(LocalTime.of(18, 0));
        salon.setOwnerId(ownerId);
        return salons.save(salon);
    }
}
