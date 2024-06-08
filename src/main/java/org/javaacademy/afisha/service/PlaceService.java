package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.dto.PlaceDtoRq;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    public void addPlace(PlaceDtoRq placeDtoRq) {
        placeRepository.savePlace(placeDtoRq.getName(),
                                  placeDtoRq.getAddress(),
                                  placeDtoRq.getCity());
    }

    public List<PlaceDto> findAllPlaces() {
        return placeRepository.findAllPlaces()
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PlaceDto findPlaceById(int id) {
        return convertToDto(placeRepository.findPlaceById(id).orElseThrow());
    }

    public PlaceDto convertToDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(place.getId());
        placeDto.setName(place.getName());
        placeDto.setAddress(place.getAddress());
        placeDto.setCity(place.getCity());
        return placeDto;
    }
}
