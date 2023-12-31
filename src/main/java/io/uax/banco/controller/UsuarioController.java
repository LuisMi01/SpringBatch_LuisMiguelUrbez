package io.uax.banco.controller;

import io.uax.banco.domain.Usuario;
import io.uax.banco.model.UsuarioDTO;
import io.uax.banco.repos.UsuarioRepository;
import io.uax.banco.service.UsuarioService;
import io.uax.banco.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        listUsuarios(model);
        return "usuario/list";
    }

    public String listUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        //total de dinero sumado
        double totalAmount = usuarios.stream()
                .mapToDouble(Usuario::getAmount)
                .sum();

        //usuario que mas ha gastado
        Map<String, Double> spenders = usuarios.stream()
                .filter(usuario -> usuario.getTransactionType().equals("Retiro"))
                .collect(Collectors.groupingBy(Usuario::getAccountId, Collectors.summingDouble(Usuario::getAmount)));

        Map.Entry<String, Double> maxSpenderEntry = spenders.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        //usuario que más ha ingresado
        Map<String, Double> depositors = usuarios.stream()
                .filter(usuario -> usuario.getTransactionType().equals("Ingreso"))
                .collect(Collectors.groupingBy(Usuario::getAccountId, Collectors.summingDouble(Usuario::getAmount)));

        Map.Entry<String, Double> maxDepositorEntry = depositors.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("maxSpender", maxSpenderEntry != null ? maxSpenderEntry.getKey() : "N/A");
        model.addAttribute("maxSpenderAmount", maxSpenderEntry != null ? maxSpenderEntry.getValue() : 0);
        model.addAttribute("maxDepositor", maxDepositorEntry != null ? maxDepositorEntry.getKey() : "N/A");
        model.addAttribute("maxDepositorAmount", maxDepositorEntry != null ? maxDepositorEntry.getValue() : 0);


        return "usuario/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("usuario") final UsuarioDTO usuarioDTO) {
        return "usuario/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuario/add";
        }
        usuarioService.create(usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.create.success"));
        return "redirect:/usuarios";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("usuario", usuarioService.get(id));
        return "usuario/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuario/edit";
        }
        usuarioService.update(id, usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.update.success"));
        return "redirect:/usuarios";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        usuarioService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("usuario.delete.success"));
        return "redirect:/usuarios";
    }

}
