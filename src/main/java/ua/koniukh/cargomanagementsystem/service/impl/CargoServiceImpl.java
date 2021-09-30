package ua.koniukh.cargomanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.repository.CargoRepository;
import ua.koniukh.cargomanagementsystem.service.CargoService;

@Service
public class CargoServiceImpl implements CargoService {

    private final CargoRepository cargoRepository;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public Cargo saveCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }
}
