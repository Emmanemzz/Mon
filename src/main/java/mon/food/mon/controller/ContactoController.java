package mon.food.mon.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class ContactoController {

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto";
    }

    @PostMapping("/contacto")
    public String enviarContacto(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String asunto,
            @RequestParam String mensaje,
            RedirectAttributes redirectAttributes) {

        // Por ahora solo mostramos el mensaje de confirmación
        redirectAttributes.addFlashAttribute("exito", 
            "¡Gracias por contactarnos, " + nombre + "! Hemos recibido tu mensaje.");

        return "redirect:/contacto";
    }
}