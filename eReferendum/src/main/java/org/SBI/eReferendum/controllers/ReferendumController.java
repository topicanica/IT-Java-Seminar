package org.SBI.eReferendum.controllers;

import java.util.ArrayList;
import java.util.List;

import org.SBI.eReferendum.exceptions.ResourceNotFoundException;
import org.SBI.eReferendum.models.City;
import org.SBI.eReferendum.models.Referendum;
import org.SBI.eReferendum.models.ReferendumQuestion;
import org.SBI.eReferendum.models.Region;
import org.SBI.eReferendum.services.ReferendumQuestionService;
import org.SBI.eReferendum.services.ReferendumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/referendums")
public class ReferendumController {

	@Autowired
	private ReferendumService referendumService;
	@Autowired
	private ReferendumQuestionService referendumQuestionService;
	@Autowired
	private PagedResourcesAssembler<Referendum> pagedResourcesAssembler;
	@Autowired
	RestTemplate restTemplate;

	@GetMapping
	public ResponseEntity<List<Referendum>> findAll() {
		return ResponseEntity.ok(referendumService.getAll());
	}

	@GetMapping("/refused/{citizenOIB}")
	public ResponseEntity<List<Referendum>> findRefused(@PathVariable String citizenOIB) {
		return ResponseEntity.ok(referendumService.findRefusedReferendums(citizenOIB));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Referendum> findById(@PathVariable Long id) {
		return referendumService.findById(id).map(referendum -> {
			return ResponseEntity.ok().body(referendum);

		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Referendum> create(Referendum referendum) {
		return ResponseEntity.status(HttpStatus.CREATED).body(referendumService.save(referendum));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Referendum> update(@PathVariable Long id, @RequestBody Referendum referendum) {
		return ResponseEntity.accepted().body(referendumService.save(referendum));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		referendumService.deleteById(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/view")
	public String viewReferendums(Model model, Pageable pageable) {
		Page<Referendum> referendumsPage = referendumService.getPage(pageable);
		model.addAttribute("referendumsPage", referendumsPage);

		PagedModel<EntityModel<Referendum>> referendumsPagedResource = pagedResourcesAssembler.toModel(referendumsPage);
		referendumsPagedResource.getNextLink().ifPresent(next -> model.addAttribute("nextLink", next.getHref()));
		referendumsPagedResource.getPreviousLink().ifPresent(prev -> model.addAttribute("prevLink", prev.getHref()));

		return "Referendums";
	}

	@GetMapping("/view/{id}")
	public String viewById(@PathVariable Long id, Model model) {
		referendumService.findById(id).map(referendum -> {
			return model.addAttribute("referendum", referendum);
		}).orElseThrow(() -> new ResourceNotFoundException("Referendum " + id + " not found"));
		return "Referendum";
	}

	@GetMapping("/view/active")
	public String findActiveReferendums(Model model) {
		if (!referendumService.findActive().isEmpty()) {
			model.addAttribute("referendums", referendumService.findActive());
			return "ActiveReferendums";
		}
		model.addAttribute("referendums", new ArrayList<>());

		return "ActiveReferendums";
	}

	@RequestMapping(value = "/create")
	public String viewReferendumForm(Model model) {
		Referendum referendum = new Referendum();
		referendum.setQuestions(new ArrayList<>());
		model.addAttribute("referendum", referendum);
		City[] cities = restTemplate.getForObject("http://eRegistry/cities/get-all", City[].class);
		Region[] regions = restTemplate.getForObject("http://eRegistry/regions/get-all", Region[].class);
		model.addAttribute("regions", regions);
		model.addAttribute("cities", cities);
		return "AddReferendum";
	}

	@RequestMapping(value = "/create", params = { "addQuestion" })
	public String addQuestion(@ModelAttribute("referendum") Referendum referendum, Model model) {
		referendum.AddQuestion();
		City[] cities = restTemplate.getForObject("http://eRegistry/cities/get-all", City[].class);
		Region[] regions = restTemplate.getForObject("http://eRegistry/regions/get-all", Region[].class);
		model.addAttribute("regions", regions);
		model.addAttribute("cities", cities);
		model.addAttribute("referendum", referendum);

		return "AddReferendum";
	}

	@PostMapping(value = "/create")
	public String saveReferendum(@ModelAttribute("referendum") Referendum referendum, BindingResult result) {

		if (result.hasErrors())
			return "AddReferendum";

//		Integer numOfEligibleVoters = restTemplate.getForObject("http://eRegistry/eligible-voters", Integer.class);
//		referendum.setNumberOfEligibleVoters(numOfEligibleVoters);
		Referendum savedReferendum = referendumService.save(referendum);
		for (ReferendumQuestion question : savedReferendum.getQuestions()) {
			question.setReferendum(savedReferendum);
			referendumQuestionService.save(question);
		}
		return "redirect:/referendums/view";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		referendumService.findById(id).map(referendum -> {
			City[] cities = restTemplate.getForObject("http://eRegistry/cities/get-all", City[].class);
			Region[] regions = restTemplate.getForObject("http://eRegistry/regions/get-all", Region[].class);
			model.addAttribute("regions", regions);
			model.addAttribute("cities", cities);
			return model.addAttribute("referendum", referendum);
		}).orElseThrow(() -> new ResourceNotFoundException("Referendum " + id + " not found"));
		return "EditReferendum";
	}

	@RequestMapping(value = "/update/{id}", params = { "addQuestion" })
	public String addEditQuestion(@ModelAttribute("referendum") Referendum referendum, Model model) {
		referendum.AddQuestion();
		City[] cities = restTemplate.getForObject("http://eRegistry/cities/get-all", City[].class);
		Region[] regions = restTemplate.getForObject("http://eRegistry/regions/get-all", Region[].class);
		model.addAttribute("regions", regions);
		model.addAttribute("cities", cities);
		model.addAttribute("referendum", referendum);
		return "EditReferendum";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("referendum") Referendum referendum,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			referendum.setId(id);
			return "EditReferendum";
		}

//		Integer numOfEligibleVoters = restTemplate.getForObject("http://eRegistry/eligible-voters", Integer.class);
//		referendum.setNumberOfEligibleVoters(numOfEligibleVoters);
		Referendum savedReferendum = referendumService.save(referendum);
		for (ReferendumQuestion question : savedReferendum.getQuestions()) {
			question.setReferendum(savedReferendum);
			referendumQuestionService.save(question);
		}
		return "redirect:/referendums/view";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		referendumService.deleteById(id);

		return "redirect:/referendums/view";
	}

}
