package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;
import ua.koniukh.cargomanagementsystem.repository.CargoRepository;

@Service
public class CargoService {

  private final CargoRepository cargoRepository;

  @Autowired
  public CargoService(CargoRepository cargoRepository) {
    this.cargoRepository = cargoRepository;
  }

  public Cargo findById(Long id) {
    return cargoRepository.getById(id);
  }

  public List<Cargo> findAll() {
    return cargoRepository.findAll();
  }

  public Cargo saveCargo(Cargo cargo) {
    return cargoRepository.save(cargo);
  }

  public void deleteById(Long id) {
    cargoRepository.deleteById(id);
  }

  public Cargo createCargo(@NotNull CargoDTO cargoDTO) {
    Cargo cargo =
        Cargo.builder()
            .weight(cargoDTO.getWeight())
            .length(cargoDTO.getLength())
            .height(cargoDTO.getHeight())
            .width(cargoDTO.getWidth())
            //               .active(true)
            .build();

    saveCargo(cargo);
    return cargo;
  }
}
