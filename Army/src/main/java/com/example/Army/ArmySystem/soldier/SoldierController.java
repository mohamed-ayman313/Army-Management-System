package com.example.Army.ArmySystem.soldier;
import com.example.Army.ArmySystem.exceptions.soldierException.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/soldier")
@AllArgsConstructor
public class SoldierController {
    private final SoldierService soldierService;
    @GetMapping("/getAllSoldiers")
    public List<SoldierGetRequest> getAllSoldiers(){
        return soldierService.getAllSoldiers();
    }
    @GetMapping("/getSoldier/{soldierId}")
    public SoldierGetRequest getSoldier2(@PathVariable("soldierId") int soldierId){
        return soldierService.getSoldier(soldierId);

    }


    @GetMapping("/getSoldier")
    public SoldierGetRequest getSoldier(@RequestParam("soldierId") int soldierId){
        return soldierService.getSoldier(soldierId);
    }
    @PostMapping("/addSoldier")
    public String addSoldier(@RequestBody SoldierRequest soldierRequest){
        try{
        soldierService.addSoldier(soldierRequest);
            return "Soldier added successfully";
        }catch (NullSoldierFirstNameException e){
            return "soldier first name cannot be null";
        }catch (NullSoldierLastNameException e){
            return "soldier last name cannot be null";
        }catch (NullSoldierArmyIdException e){
            return "soldier army id cannot be null";
        }catch (NullSoldierPositionException e){
            return "soldier position cannot be null";
        }catch (ExistSoldierArmyIdException e){
            return "soldier army id already exists";
        }catch (NotFoundSoldierPositionException e){
            return "No such position name exists";
        }
    }
    @PostMapping("/addMultiSoldiers")
    public String addMultiSoldiers(@RequestBody List<SoldierRequest> soldiers){
        try {
            soldierService.addMultiSoldiers(soldiers);
            return "Soldiers added successfully";
        }catch (NullSoldierFirstNameException e){
            return "soldier first name cannot be null";
        }catch (NullSoldierLastNameException e){
            return "soldier last name cannot be null";
        }catch (NullSoldierArmyIdException e){
            return "soldier army id cannot be null";
        }catch (NullSoldierPositionException e){
            return "soldier position cannot be null";
        }catch (ExistSoldierArmyIdException e){
            return "soldier army id already exists";
        }catch (NotFoundSoldierPositionException e){
            return "No such position name exists";
        }
    }
    @DeleteMapping("/deleteSoldier")
    public String deleteSoldier(@RequestParam("soldierId") int soldierId){
        try {
            soldierService.deleteSoldier(soldierId);
            return "Soldier deleted successfully";
        }catch (NotFoundSoldierException e){
            return "Soldier not found";
        }
    }
    @PutMapping("/updateSoldier")
    public String updateSoldier(@RequestParam("soldierId") int soldierId, @RequestParam("soldierFirstName") String soldierFirstName, @RequestParam("soldierLastName") String soldierLastName,@RequestParam(value = "soldierArmyId",required = false) String soldierArmyId, @RequestParam(value = "positionName",required = false) String positionName){
        try {
            soldierService.updateSoldier(soldierId, soldierFirstName, soldierLastName, soldierArmyId, positionName);
            return "Soldier updated successfully";
        }catch (NotFoundSoldierException e){
            return "Soldier not found";
        }catch (NotFoundSoldierPositionException e){
            return "No such position name exists";
        }
    }
}
