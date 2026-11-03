package com.urbansalon.service.impl;

import com.urbansalon.modal.Salon;
import com.urbansalon.payload.dto.SalonDTO;
import com.urbansalon.payload.dto.UserDTO;
import com.urbansalon.repository.SalonRepository;
import com.urbansalon.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        Salon existingSalon = getSalonById(salonId);
        if (!Objects.equals(existingSalon.getOwnerId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Salon belongs to another owner");
        }

        if (salon.getName() != null) {
            existingSalon.setName(salon.getName());
        }
        if (salon.getAddress() != null) {
            existingSalon.setAddress(salon.getAddress());
        }
        if (salon.getEmail() != null) {
            existingSalon.setEmail(salon.getEmail());
        }
        if (salon.getCity() != null) {
            existingSalon.setCity(salon.getCity());
        }
        if (salon.getImages() != null) {
            existingSalon.setImages(salon.getImages());
        }
        if (salon.getOpenTime() != null) {
            existingSalon.setOpenTime(salon.getOpenTime());
        }
        if (salon.getCloseTime() != null) {
            existingSalon.setCloseTime(salon.getCloseTime());
        }
        if (salon.getPhoneNumber() != null) {
            existingSalon.setPhoneNumber(salon.getPhoneNumber());
        }
        return salonRepository.save(existingSalon);
    }

    @Override
    public Salon getSalonById(Long salonId) {
        return salonRepository.findById(salonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Salon not found"));
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findFirstByOwnerIdOrderByIdAsc(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalon(city);
    }
}
