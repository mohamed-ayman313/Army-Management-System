package com.example.Army.ArmySystem.officer;
import com.example.Army.ArmySystem.exceptions.officerException.*;
import com.example.Army.ArmySystem.position.Position;
import com.example.Army.ArmySystem.position.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OfficerService {
    private final OfficerRepository officerRepository;
    private final PositionRepository positionRepository;
    public List<OfficerGetRequest> getAllOfficers() {
        List<Officer> officers = officerRepository.findAll();
        List<OfficerGetRequest> officerGetRequests = new ArrayList<>();
        for (Officer officer : officers) {
            officerGetRequests.add(new OfficerGetRequest(
                    officer.getOfficerFirstName(),
                    officer.getOfficerLastName(),
                    officer.getOfficerArmyId(),
                    officer.getPositions()
            ));
        }
        return officerGetRequests;
    }

    public OfficerGetRequest getOfficer(int id) {
        Officer officer=officerRepository.findById(id).get();
       return new OfficerGetRequest(
               officer.getOfficerFirstName(),
                officer.getOfficerLastName(),
                officer.getOfficerArmyId(),
               officer.getPositions()

        );

    }

    public void addOfficer(OfficerRequest officerRequest) throws NullOfficerFirstNameException, NullOfficerLastNameException, NullOfficerArmyIdException, NullOfficerPositionException, ExistOfficerArmyIdException, NotFoundOfficerPositionException {
        if(officerRequest.getOfficerFirstName()==null || officerRequest.getOfficerFirstName().isEmpty()) {
            throw new NullOfficerFirstNameException();
        }
        if(officerRequest.getOfficerLastName()==null || officerRequest.getOfficerLastName().isEmpty()){
            throw new NullOfficerLastNameException();
        }
        if(officerRequest.getOfficerArmyId()==null || officerRequest.getOfficerArmyId().isEmpty()) {
            throw new NullOfficerArmyIdException();
        }

        for(Position position : officerRequest.getPositions()) {
            if(position.getPositionName()==null || position.getPositionName().isEmpty()) {
                throw new NullOfficerPositionException();
            }
        }
        if(officerRepository.findOfficerByOfficerArmyId(officerRequest.getOfficerArmyId()).isPresent()){
            throw new ExistOfficerArmyIdException();
        }
        for(Position position : officerRequest.getPositions()) {
            if(!(positionRepository.findById(position.getPositionName()).isPresent())){
                throw new NotFoundOfficerPositionException();
            }
        }
        Officer officer = new Officer(
                officerRequest.getOfficerFirstName(),
                officerRequest.getOfficerLastName(),
                officerRequest.getOfficerArmyId(),
                officerRequest.getPositions()

        );
        officerRepository.save(officer);
        //you could remove this section if you don't want to get officers from position object
        for (Position position : officer.getPositions()) {
            Position pos = positionRepository.findById(position.getPositionName()).get();
            pos.setOfficer(officer);
            positionRepository.save(pos);
        }
    }

    public void addMultiOfficers(List<OfficerRequest> officerRequests) throws NullOfficerFirstNameException, NullOfficerLastNameException, NullOfficerArmyIdException, NullOfficerPositionException, ExistOfficerArmyIdException, NotFoundOfficerPositionException {
        for(OfficerRequest officerRequest : officerRequests) {
            if(officerRequest.getOfficerFirstName()==null || officerRequest.getOfficerFirstName().isEmpty()) {
                throw new NullOfficerFirstNameException();
            }
            if(officerRequest.getOfficerLastName()==null || officerRequest.getOfficerLastName().isEmpty()){
                throw new NullOfficerLastNameException();
            }
            if(officerRequest.getOfficerArmyId()==null || officerRequest.getOfficerArmyId().isEmpty()) {
                throw new NullOfficerArmyIdException();
            }
            for(Position position : officerRequest.getPositions()) {
                if(position.getPositionName()==null || position.getPositionName().isEmpty()) {
                    throw new NullOfficerPositionException();
                }
            }
            if(officerRepository.findOfficerByOfficerArmyId(officerRequest.getOfficerArmyId()).isPresent()){
                throw new ExistOfficerArmyIdException();
            }
            for(Position position : officerRequest.getPositions()) {
                if(!(positionRepository.findById(position.getPositionName()).isPresent())){
                    throw new NotFoundOfficerPositionException();
                }
            }
        }
        List<Officer> officers = new ArrayList<>();
        for (OfficerRequest officerRequest : officerRequests) {
            officers.add(new Officer(officerRequest.getOfficerFirstName(),
                                    officerRequest.getOfficerLastName(),
                                    officerRequest.getOfficerArmyId(),
                                    officerRequest.getPositions()
                        )
            );
        }
        officerRepository.saveAll(officers);
        //you could remove this section if you don't want to get officers from position object
        for(Officer officer : officers) {
            for (Position position : officer.getPositions()) {
                Position pos = positionRepository.findById(position.getPositionName()).get();
                pos.setOfficer(officer);
                positionRepository.save(pos);
            }
        }

    }
//    @Transactional
    public void deleteOfficer(int officerId) throws NotFoundOfficerException {
        if(!(officerRepository.findById(officerId).isPresent())) {
            throw new NotFoundOfficerException();
        }
        List<Position> positions = officerRepository.findById(officerId).get().getPositions();
        for (Position position : positions) {
            position.setOfficer(null);
            positionRepository.save(position);
        }
        officerRepository.deleteById(officerId);
    }
    @Transactional
    public void updateOfficer(int officerId, String officerFirstName, String officerLastName, String officerArmyId, List<String> positionNames) throws NotFoundOfficerException, NotFoundOfficerPositionException {
        if(!(officerRepository.findById(officerId).isPresent())) {
            throw new NotFoundOfficerException();
        }

        Officer officer=officerRepository.findById(officerId).get();
        officer.setOfficerFirstName(officerFirstName);
        officer.setOfficerLastName(officerLastName);
        if(officerArmyId!=null) {
            officer.setOfficerArmyId(officerArmyId);
        }
        if(positionNames!=null) {
            List<Position> positions = new ArrayList<>();
            for(String positionName : positionNames) {
                if(!(positionRepository.findById(positionName).isPresent())){
                    throw new NotFoundOfficerPositionException();
                }
                positions.add(positionRepository.findById(positionName).get());
            }
            for(Position position : officer.getPositions()) {
                positionRepository.findById(position.getPositionName()).get().setOfficer(null);
            }
            for(String positionName : positionNames) {
                positionRepository.findById(positionName).get().setOfficer(officer);
            }

            officer.setPositions(positions);

        }
    }
}
