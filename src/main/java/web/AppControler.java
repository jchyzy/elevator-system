package web;

import model.Direction;
import model.ElevatorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppControler {

    private ElevatorSystem system;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/system", params = {"elevators", "floors"})
    public String system(Model model,
                         @RequestParam(value = "elevators") int elevators,
                         @RequestParam(value = "floors") int floors) {
        logger.info("Configuration set. Elevators: " + elevators + ", floors: " + floors);

        system = new ElevatorSystem(elevators, floors);
        addAttributesToModel(model);
        return "system";
    }

    @PostMapping(value = "/system", params = {"floor", "direction"})
    public String systemPickup(Model model,
                               @RequestParam(value = "floor") int floor,
                               @RequestParam(value = "direction") String direction) {
        logger.info("Pickup command executed. Floor: " + floor + ", direction: " + direction);

        Direction directionEnum;
        if (direction.equals("up")) {
            directionEnum = Direction.UP;
        } else {
            directionEnum = Direction.DOWN;
        }

        system.pickup(floor, directionEnum);
        addAttributesToModel(model);
        return "system";
    }

    @PostMapping(value = "/system", params = {"elevator", "destination"})
    public String systemUpdate(Model model,
                               @RequestParam(value = "elevator") int elevator,
                               @RequestParam(value = "destination") int destination) {
        logger.info("Update command executed. Elevator: " + elevator + ", destination: " + destination);

        system.update(elevator, destination);
        addAttributesToModel(model);
        return "system";
    }

    @PostMapping(value = "/system", params = {"steps"})
    public String systemSteps(Model model,
                              @RequestParam(value = "steps") int steps) {
        logger.info("Steps command executed. Steps: " + steps);

        system.step(steps);
        addAttributesToModel(model);
        return "system";
    }

    private void addAttributesToModel(Model model) {
        model.addAttribute("elevators", system.getElevators());
        model.addAttribute("elevatorsNumber", system.getElevatorsNumber());
        model.addAttribute("floorsNumber", system.getFloorsNumber());
    }
}
