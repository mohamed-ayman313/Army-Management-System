package com.example.Army.ArmySystem.ncofficer;
import com.example.Army.ArmySystem.exceptions.ncoException.*;
import com.example.Army.ArmySystem.position.Position;
import com.example.Army.ArmySystem.position.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NcofficerService {
    private final NcofficerRepository ncofficerRepository;
    private final PositionRepository positionRepository;
    public List<NcofficerGetRequest> getAllNcofficers() {
        List<Ncofficer> ncofficers = ncofficerRepository.findAll();
        List<NcofficerGetRequest> ncofficerGetRequests = new ArrayList<>();

        for (Ncofficer ncofficer : ncofficers) {
            ncofficerGetRequests.add(
                    new NcofficerGetRequest(
                            ncofficer.getNcoFirstName(),
                            ncofficer.getNcoLastName(),
                            ncofficer.getNcoArmyId(),
                            ncofficer.getPositions()
                    )
            );
        }
        return ncofficerGetRequests;
    }

    public NcofficerGetRequest getNcofficer(int id) {
        Ncofficer ncofficer =ncofficerRepository.findById(id).get();
        NcofficerGetRequest ncofficerGetRequest = new NcofficerGetRequest(
                ncofficer.getNcoFirstName(),
                ncofficer.getNcoLastName(),
                ncofficer.getNcoArmyId(),
                ncofficer.getPositions()
        );
        return ncofficerGetRequest;
    }

    public void addNcofficer(NcofficerRequest ncofficerRequest) throws NullNcofficerFirstNameException, NullNcofficerLastNameException, NullNcofficerArmyIdException, NullNcofficerPositionException, ExistNcofficerArmyIdException, NotFoundNcofficerPositionException {
        if(ncofficerRequest.getNcoFirstName()==null || ncofficerRequest.getNcoFirstName().isEmpty()){
            throw new NullNcofficerFirstNameException();
        }
        if(ncofficerRequest.getNcoLastName()==null || ncofficerRequest.getNcoLastName().isEmpty()){
            throw new NullNcofficerLastNameException();
        }
        if(ncofficerRequest.getNcoArmyId()==null || ncofficerRequest.getNcoArmyId().isEmpty()){
            throw new NullNcofficerArmyIdException();
        }
        for(Position position : ncofficerRequest.getPositions()){
            if(position.getPositionName()==null || position.getPositionName().isEmpty()){
                throw new NullNcofficerPositionException();
            }
        }
        if(ncofficerRepository.findNcofficerByNcoArmyId(ncofficerRequest.getNcoArmyId()).isPresent()){
            throw new ExistNcofficerArmyIdException();
        }
        for(Position position : ncofficerRequest.getPositions()){
            if(!(positionRepository.findById(position.getPositionName()).isPresent())){
                throw new NotFoundNcofficerPositionException();
            }
        }
        Ncofficer ncofficer = new Ncofficer(ncofficerRequest.getNcoFirstName(),
                                        ncofficerRequest.getNcoLastName(),
                                        ncofficerRequest.getNcoArmyId(),
                                        ncofficerRequest.getPositions()
                );
        ncofficerRepository.save(ncofficer);
        //you could remove this section if you don't want to get ncofficer from position object
        for (Position position : ncofficer.getPositions()) {
            Position pos = positionRepository.findById(position.getPositionName()).get();
            pos.getNcofficers().add(ncofficer);
            positionRepository.save(pos);
        }
    }

    public void addMultiNcofficers(List<NcofficerRequest> ncofficerRequests) throws NullNcofficerFirstNameException, NullNcofficerLastNameException, NullNcofficerArmyIdException, NullNcofficerPositionException, ExistNcofficerArmyIdException, NotFoundNcofficerPositionException {
        for(NcofficerRequest ncofficerRequest : ncofficerRequests){
            if(ncofficerRequest.getNcoFirstName()==null || ncofficerRequest.getNcoFirstName().isEmpty()){
                throw new NullNcofficerFirstNameException();
            }
            if(ncofficerRequest.getNcoLastName()==null || ncofficerRequest.getNcoLastName().isEmpty()){
                throw new NullNcofficerLastNameException();
            }
            if(ncofficerRequest.getNcoArmyId()==null || ncofficerRequest.getNcoArmyId().isEmpty()){
                throw new NullNcofficerArmyIdException();
            }
            for(Position position : ncofficerRequest.getPositions()){
                if(position.getPositionName()==null || position.getPositionName().isEmpty()){
                    throw new NullNcofficerPositionException();
                }
            }
            if(ncofficerRepository.findNcofficerByNcoArmyId(ncofficerRequest.getNcoArmyId()).isPresent()){
                throw new ExistNcofficerArmyIdException();
            }
            for(Position position : ncofficerRequest.getPositions()){
                if(!(positionRepository.findById(position.getPositionName()).isPresent())){
                    throw new NotFoundNcofficerPositionException();
                }
            }

        }
        List<Ncofficer> ncofficers = new ArrayList<>();
        for (NcofficerRequest ncofficerRequest : ncofficerRequests) {
            ncofficers.add(new Ncofficer(ncofficerRequest.getNcoFirstName(),
                                        ncofficerRequest.getNcoLastName(),
                                        ncofficerRequest.getNcoArmyId(),
                                        ncofficerRequest.getPositions()
                                    )
            );
        }
        ncofficerRepository.saveAll(ncofficers);

        //you could remove this section if you don't want to get ncofficer from position object
        for (Ncofficer ncofficer : ncofficers) {
            for (Position position : ncofficer.getPositions()) {
                    Position pos = positionRepository.findById(position.getPositionName()).get();
                    pos.getNcofficers().add(ncofficer);
                    positionRepository.save(pos);

            }
        }
    }
    public void deleteNcofficer(int ncoId) throws NotFoundNcofficerException {
        if(!(ncofficerRepository.findById(ncoId).isPresent())){
            throw new NotFoundNcofficerException();
        }
        //you should set null before delete as mapped objects can't be deleted
        List<Position> positions = ncofficerRepository.findById(ncoId).get().getPositions();
        for(Position position : positions){
            position.setNcofficers(null);
            positionRepository.save(position);
        }
        ncofficerRepository.deleteById(ncoId);
    }
    @Transactional
    public void updateNcofficer(int ncoId, String ncoFirstName, String ncoLastName, String ncoArmyId, List<String> positionNames) throws NotFoundNcofficerPositionException, NotFoundNcofficerException {
        if(!(ncofficerRepository.findById(ncoId).isPresent())){
            throw new NotFoundNcofficerException();
        }
        Ncofficer ncofficer = ncofficerRepository.findById(ncoId).get();
        ncofficer.setNcoFirstName(ncoFirstName);
        ncofficer.setNcoLastName(ncoLastName);
        if(ncoArmyId!=null) {
            ncofficer.setNcoArmyId(ncoArmyId);
        }
        if(positionNames!=null){
            List<Position> positions = new ArrayList<>();
            for(String positionName : positionNames){
                if(!(positionRepository.findById(positionName).isPresent())){
                    throw new NotFoundNcofficerPositionException();
                }
                positions.add(positionRepository.findById(positionName).get());
            }
            for(Position position : ncofficer.getPositions()){
                positionRepository.findById(position.getPositionName()).get().getNcofficers().remove(ncofficer);
            }
            for(String positionName : positionNames){
                positionRepository.findById(positionName).get().getNcofficers().add(ncofficer);
            }

            ncofficer.setPositions(positions);
        }
    }
}
