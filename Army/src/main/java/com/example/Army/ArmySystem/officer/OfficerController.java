package com.example.Army.ArmySystem.officer;
import com.example.Army.ArmySystem.exceptions.officerException.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/officer")
@AllArgsConstructor
public class OfficerController {
    private final OfficerService officerService;
    @GetMapping("/getAllOfficers")
    public List<OfficerGetRequest> getAllOfficers(){
        return officerService.getAllOfficers();
    }
    @GetMapping("/getOfficer/{officerId}")
    public OfficerGetRequest getOfficer2(@PathVariable("officerId") int officerId){
        return officerService.getOfficer(officerId);
    }
    @GetMapping("/getOfficer")
    public OfficerGetRequest getOfficer(@RequestParam("officerId") int officerId){
        return officerService.getOfficer(officerId);
    }
    @PostMapping("/addOfficer")
    public String addOfficer(@RequestBody OfficerRequest officerRequest){
        try {
            officerService.addOfficer(officerRequest);
            return "officer added successfully";
        }catch (NullOfficerFirstNameException e){
            return "officer first name cannot be null";
        }catch (NullOfficerLastNameException e){
            return "officer last name cannot be null";
        }catch (NullOfficerArmyIdException e){
            return "officer army id cannot be null";
        }catch (NullOfficerPositionException e){
            return "officer position cannot be null";
        }catch (ExistOfficerArmyIdException e){
            return "officer army id already exist";
        }catch (NotFoundOfficerPositionException e){
            return "No such position name exists";
        }
    }
    @PostMapping("/addMultiOfficers")
    public String addMultiOfficers(@RequestBody List<OfficerRequest> officers){
        try {
            officerService.addMultiOfficers(officers);
            return "Officers added successfully";
        }catch (NullOfficerFirstNameException e){
            return "officer first name cannot be null";
        }catch (NullOfficerLastNameException e){
            return "officer last name cannot be null";
        }catch (NullOfficerArmyIdException e){
            return "officer army id cannot be null";
        }catch (NullOfficerPositionException e){
            return "officer position cannot be null";
        }catch (ExistOfficerArmyIdException e){
            return "officer army id already exist";
        }catch (NotFoundOfficerPositionException e){
            return "No such position name exists";
        }
    }
    @DeleteMapping("/deleteOfficer")
    public String deleteOfficer(@RequestParam("officerId") int officerId){
        try {
            officerService.deleteOfficer(officerId);
            return "officer deleted successfully";
        }catch (NotFoundOfficerException e){
            return "Officer not found";
        }
    }

    @PutMapping("/updateOfficer")
    public String updateOfficer(@RequestParam("officerId") int officerId, @RequestParam("officerFirstName") String officerFirstName, @RequestParam("officerLastName") String officerLastName, @RequestParam(value = "officerArmyId",required = false) String officerArmyId, @RequestParam(value = "positionNames",required = false) List<String> positionNames){
        try {
            officerService.updateOfficer(officerId, officerFirstName, officerLastName, officerArmyId, positionNames);
            return "officer updated successfully";
        }catch (NotFoundOfficerException e){
            return "Officer not found";
        }catch (NotFoundOfficerPositionException e){
            return "No such position name exists";
        }
    }
}
