package pw.edu.wsg.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/home")
public class ScheduleControler {

    @GetMapping("")
    public String showHomepage(Model model){
        model.addAttribute("schedule", new Schedule());
        return "home-page";
    }
}
