package com.example.Army.ArmySystem.soldier;
import com.example.Army.ArmySystem.exceptions.soldierException.*;
import com.example.Army.ArmySystem.position.Position;
import com.example.Army.ArmySystem.position.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SoldierService {
    private final SoldierRepository soldierRepository;
    private final PositionRepository positionRepository;
    public List<SoldierGetRequest> getAllSoldiers() {
        List<Soldier> soldiers = soldierRepository.findAll();
        List<SoldierGetRequest> soldierGetRequests = new ArrayList<>();
        for (Soldier soldier : soldiers) {
            soldierGetRequests.add(
                    new SoldierGetRequest(
                            soldier.getSoldierFirstName(),
                            soldier.getSoldierLastName(),
                            soldier.getSoldierArmyId(),
                            soldier.getPosition()
                    )
            );
        }
        return soldierGetRequests;
    }

    public SoldierGetRequest getSoldier(int id) {
        Soldier soldier = soldierRepository.findById(id).orElseThrow(() -> new IllegalStateException("not found soldier"));
        SoldierGetRequest soldierGetRequest = new SoldierGetRequest(
                soldier.getSoldierFirstName(),
                soldier.getSoldierLastName(),
                soldier.getSoldierArmyId(),
                soldier.getPosition()
        );
        return soldierGetRequest;
    }

    public void addSoldier(SoldierRequest soldierRequest) throws NullSoldierFirstNameException, NullSoldierLastNameException, NullSoldierArmyIdException, NullSoldierPositionException, ExistSoldierArmyIdException, NotFoundSoldierPositionException {
        if(soldierRequest.getSoldierFirstName()==null || soldierRequest.getSoldierFirstName().isEmpty()){
            throw new NullSoldierFirstNameException();
        }
        if(soldierRequest.getSoldierLastName()==null || soldierRequest.getSoldierLastName().isEmpty()){
            throw new NullSoldierLastNameException();
        }
        if(soldierRequest.getSoldierArmyId()==null || soldierRequest.getSoldierArmyId().isEmpty()){
            throw new NullSoldierArmyIdException();
        }
        if(soldierRequest.getPosition().getPositionName()==null || soldierRequest.getPosition().getPositionName().isEmpty()){
            throw new NullSoldierPositionException();
        }
        if(soldierRepository.findSoldierBySoldierArmyId(soldierRequest.getSoldierArmyId()).isPresent()){
            throw new ExistSoldierArmyIdException();
        }
        if(!(positionRepository.findById(soldierRequest.getPosition().getPositionName()).isPresent())){
            throw new NotFoundSoldierPositionException();
        }
        Soldier soldier = new Soldier(
                soldierRequest.getSoldierFirstName(),
                soldierRequest.getSoldierLastName(),
                soldierRequest.getSoldierArmyId(),
                soldierRequest.getPosition()
        );
        soldierRepository.save(soldier);
        Position pos = positionRepository.findById(soldier.getPosition().getPositionName()).get();
        pos.setSoldier(soldier);
        positionRepository.save(pos);
    }

    public void addMultiSoldiers(List<SoldierRequest> soldierRequests) throws NullSoldierFirstNameException, NullSoldierLastNameException, NullSoldierArmyIdException, NullSoldierPositionException, ExistSoldierArmyIdException, NotFoundSoldierPositionException {
        for (SoldierRequest soldierRequest : soldierRequests) {
            if(soldierRequest.getSoldierFirstName()==null || soldierRequest.getSoldierFirstName().isEmpty()){
                throw new NullSoldierFirstNameException();
            }
            if(soldierRequest.getSoldierLastName()==null || soldierRequest.getSoldierLastName().isEmpty()){
                throw new NullSoldierLastNameException();
            }
            if(soldierRequest.getSoldierArmyId()==null || soldierRequest.getSoldierArmyId().isEmpty()){
                throw new NullSoldierArmyIdException();
            }
            if(soldierRequest.getPosition().getPositionName()==null || soldierRequest.getPosition().getPositionName().isEmpty()){
                throw new NullSoldierPositionException();
            }
            if(soldierRepository.findSoldierBySoldierArmyId(soldierRequest.getSoldierArmyId()).isPresent()){
                throw new ExistSoldierArmyIdException();
            }
            if(!(positionRepository.findById(soldierRequest.getPosition().getPositionName()).isPresent())){
                throw new NotFoundSoldierPositionException();
            }
        }
        List<Soldier> soldiers = new ArrayList<>(soldierRequests.size());
        for(SoldierRequest soldierRequest : soldierRequests) {
            Position p = positionRepository.findById(soldierRequest.getPosition().getPositionName()).get();
            soldiers.add(new Soldier(soldierRequest.getSoldierFirstName(),
                    soldierRequest.getSoldierLastName(),
                    soldierRequest.getSoldierArmyId(), p
            ));
        }
        soldierRepository.saveAll(soldiers);
        //you could remove this section if you don't want to get soldier from position object
        for(Soldier soldier : soldiers) {
                        Position p = positionRepository.findById(soldier.getPosition().getPositionName()).get();
                        p.setSoldier(soldier);
                        positionRepository.save(p);
        }
    }

    public void deleteSoldier(int soldierId) throws NotFoundSoldierException {
        if(!(soldierRepository.findById(soldierId).isPresent())){
            throw new NotFoundSoldierException();
        }
        Position position = soldierRepository.findById(soldierId).get().getPosition();
        position.setSoldier(null);
        positionRepository.save(position);
        soldierRepository.deleteById(soldierId);
    }
    @Transactional //we don't need to use save method
    public void updateSoldier(int soldierId, String soldierFirstName, String soldierLastName, String soldierArmyId, String positionName) throws NotFoundSoldierException, NotFoundSoldierPositionException {
        if(!(soldierRepository.findById(soldierId).isPresent())){
            throw new NotFoundSoldierException();
        }
        Soldier soldier= soldierRepository.findById(soldierId).get();
        soldier.setSoldierFirstName(soldierFirstName);
        soldier.setSoldierLastName(soldierLastName);
        if(soldierArmyId!=null) {
            soldier.setSoldierArmyId(soldierArmyId);
        }

        if(positionName!=null) {
            if(!(positionRepository.findById(positionName).isPresent())){
                throw new NotFoundSoldierPositionException();
            }
            positionRepository.findById(soldier.getPosition().getPositionName()).get().setSoldier(null);
            positionRepository.findById(positionName).get().setSoldier(soldier);
            soldier.setPosition(positionRepository.findById(positionName).get());
        }
    }

}
