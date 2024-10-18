package com.example.Army.ArmySystem.ncofficer;


import com.example.Army.ArmySystem.exceptions.ncoException.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ncofficer")
@AllArgsConstructor
public class NcofficerController {
    private final NcofficerService ncofficerService;
    @GetMapping("/getAllNcofficers")
    public List<NcofficerGetRequest> getAllNcofficers(){
        return ncofficerService.getAllNcofficers();
    }
    @GetMapping("/getNcofficer/{ncoId}")
    public NcofficerGetRequest getNcofficer2(@PathVariable("ncoId") int ncoId){
        return ncofficerService.getNcofficer(ncoId);
    }
    @GetMapping("/getNcofficer")
    public NcofficerGetRequest getNcofficer(@RequestParam("ncoId") int ncoId){
        return ncofficerService.getNcofficer(ncoId);
    }
    @PostMapping("/addNcofficer")
    public String addNcofficer(@RequestBody NcofficerRequest ncofficerRequest){
        try {
            ncofficerService.addNcofficer(ncofficerRequest);
            return "ncoffer added successfully";
        }catch (NullNcofficerFirstNameException e){
            return "ncofficer first name cannot be null";
        }catch (NullNcofficerLastNameException e){
            return "ncofficer last name cannot be null";
        }catch (NullNcofficerArmyIdException e){
            return "ncofficer army id cannot be null";
        }catch (NullNcofficerPositionException e){
            return "ncofficer position cannot be null";
        }catch (ExistNcofficerArmyIdException e){
            return "ncofficer army id already exists";
        }catch (NotFoundNcofficerPositionException e){
            return "no such position name exists";
        }
    }
    @PostMapping("/addMultiNcofficers")
    public String addMultiNcofficers(@RequestBody List<NcofficerRequest> ncofficerRequests){
        try{
            ncofficerService.addMultiNcofficers(ncofficerRequests);
            return "ncofficer added successfully";
        }catch (NullNcofficerFirstNameException e){
            return "ncofficer first name cannot be null";
        }catch (NullNcofficerLastNameException e){
            return "ncofficer last name cannot be null";
        }catch (NullNcofficerArmyIdException e){
            return "ncofficer army id cannot be null";
        }catch (NullNcofficerPositionException e){
            return "ncofficer position cannot be null";
        }catch (ExistNcofficerArmyIdException e){
            return "ncofficer army id already exists";
        }catch (NotFoundNcofficerPositionException e){
            return "no such position name exists";
        }
    }
    @DeleteMapping("/deleteNcofficer")
    public String deleteNcofficer(@RequestParam("ncoId") int ncoId){
        try{
            ncofficerService.deleteNcofficer(ncoId);
            return "ncofficer deleted successfully";
        }catch (NotFoundNcofficerException e){
            return "ncofficer not found";
        }
    }
    @PutMapping("/updateNcofficer")
    public String updateNcofficer(@RequestParam("ncoId") int ncoId, @RequestParam("ncoFirstName") String ncoFirstName, @RequestParam("ncoLastName") String ncoLastName, @RequestParam(value = "ncoArmyId",required = false) String ncoArmyId, @RequestParam(value = "positionNames",required = false) List<String> positionNames){
        try {
            ncofficerService.updateNcofficer(ncoId, ncoFirstName, ncoLastName, ncoArmyId, positionNames);
            return "ncofficer updated successfully";
        }catch (NotFoundNcofficerException e){
            return "ncofficer not found";
        }catch (NotFoundNcofficerPositionException e){
            return "No such position name exists\"";
        }
    }

}
