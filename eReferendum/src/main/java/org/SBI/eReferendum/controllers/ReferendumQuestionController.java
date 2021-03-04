package org.SBI.eReferendum.controllers;

import java.util.List;

import org.SBI.eReferendum.exceptions.ResourceNotFoundException;
import org.SBI.eReferendum.models.QuestionValue;
import org.SBI.eReferendum.models.ReferendumQuestion;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/referendum-questions")
public class ReferendumQuestionController {

	@Autowired
	ReferendumQuestionService referendumQuestionService;

	@Autowired
	ReferendumService referendumService;

	@Autowired
	private PagedResourcesAssembler<ReferendumQuestion> pagedResourcesAssembler;

	@GetMapping
	public ResponseEntity<List<ReferendumQuestion>> findAll() {
		return ResponseEntity.ok(referendumQuestionService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReferendumQuestion> findById(@PathVariable Long id) {
		return referendumQuestionService.findById(id).map(referendumQuestion -> {
			return ResponseEntity.ok().body(referendumQuestion);

		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<ReferendumQuestion> create(ReferendumQuestion referendumQuestion) {
		return ResponseEntity.status(HttpStatus.CREATED).body(referendumQuestionService.save(referendumQuestion));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReferendumQuestion> update(@PathVariable Long id,
			@RequestBody ReferendumQuestion referendumQuestion) {
		return ResponseEntity.accepted().body(referendumQuestionService.save(referendumQuestion));
	}

	@PostMapping("/{id}/update-vote")
	public ResponseEntity<ReferendumQuestion> updateQuestionVote(@PathVariable Long id,
			@RequestBody QuestionValue value) {

		return referendumQuestionService.findById(id).map(referendumQuestion -> {
			referendumQuestionService.increaseVotes(referendumQuestion, value);
			return ResponseEntity.accepted().body(referendumQuestionService.save(referendumQuestion));

		}).orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		referendumQuestionService.deleteById(id);

		return ResponseEntity.accepted().build();
	}

	@GetMapping("/view")
	public String viewCitizens(Model model, Pageable pageable) {
		Page<ReferendumQuestion> referendumQuestionsPage = referendumQuestionService.getPage(pageable);
		model.addAttribute("questionsPage", referendumQuestionsPage);

		PagedModel<EntityModel<ReferendumQuestion>> questionsPagedResource = pagedResourcesAssembler
				.toModel(referendumQuestionsPage);
		questionsPagedResource.getNextLink().ifPresent(next -> model.addAttribute("nextLink", next.getHref()));
		questionsPagedResource.getPreviousLink().ifPresent(prev -> model.addAttribute("prevLink", prev.getHref()));

		return "Questions";
	}

	@GetMapping("/view/{id}")
	public String viewById(@PathVariable Long id, Model model) {
		referendumQuestionService.findById(id).map(question -> {
			return model.addAttribute("question", question);
		}).orElseThrow(() -> new ResourceNotFoundException("Question " + id + " not found"));
		return "Question";
	}

	@GetMapping("/referendum/{id}")
	public String viewQuestionsByReferendumId(@PathVariable Long id, Model model) {
		referendumService.findById(id).map(referendum -> {
			return model.addAttribute("questions", referendumQuestionService.findByReferendum(referendum));
		}).orElseThrow(() -> new ResourceNotFoundException("Question " + id + " not found"));
		return "ReferendumQuestions";

	}

}