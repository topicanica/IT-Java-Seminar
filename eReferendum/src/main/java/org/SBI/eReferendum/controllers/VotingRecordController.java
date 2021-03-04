package org.SBI.eReferendum.controllers;

import java.util.List;

import org.SBI.eReferendum.models.VotingRecord;
import org.SBI.eReferendum.services.VotingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/voting-records")
public class VotingRecordController {

	@Autowired
	private VotingRecordService votingRecordService;

//	@Autowired
//	private PagedResourcesAssembler<VotingRecord> pagedResourcesAssembler;

	@GetMapping
	public ResponseEntity<List<VotingRecord>> findAll() {
		return ResponseEntity.ok(votingRecordService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VotingRecord> findById(@PathVariable Long id) {
		return votingRecordService.findById(id).map(record -> {
			return ResponseEntity.ok().body(record);

		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<VotingRecord> create(VotingRecord votingRecord) {
		return ResponseEntity.status(HttpStatus.CREATED).body(votingRecordService.save(votingRecord));
	}

	@PutMapping("/{id}")
	public ResponseEntity<VotingRecord> update(@PathVariable Long id, @RequestBody VotingRecord votingRecord) {
		return ResponseEntity.accepted().body(votingRecordService.save(votingRecord));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		votingRecordService.deleteById(id);

		return ResponseEntity.accepted().build();
	}

//	@GetMapping("/view")
//	public String viewCitizens(Model model, Pageable pageable) {
//		Page<VotingRecord> votingRecordsPage = votingRecordService.getPage(pageable);
//		model.addAttribute("recordsPage", votingRecordsPage);
//
//		PagedModel<EntityModel<VotingRecord>> recordsPagedResource = pagedResourcesAssembler.toModel(votingRecordsPage);
//		recordsPagedResource.getNextLink().ifPresent(next -> model.addAttribute("nextLink", next.getHref()));
//		recordsPagedResource.getPreviousLink().ifPresent(prev -> model.addAttribute("prevLink", prev.getHref()));
//
//		return "VotingRecords";
//	}
//
//	@GetMapping("/view/{id}")
//	public String viewById(@PathVariable Long id, Model model) {
//		votingRecordService.findById(id).map(record -> {
//			return model.addAttribute("record", record);
//		}).orElseThrow(() -> new ResourceNotFoundException("Question " + id + " not found"));
//		return "VotingRecord";
//	}

}
