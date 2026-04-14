package mon.food.mon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class InicioController {
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
    

    @GetMapping("/inicio")
    public String inicioPorNombre() {
        return "index";
    }
    
}
