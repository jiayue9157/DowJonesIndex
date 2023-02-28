package com.yuejia.stocks.controller;

import com.yuejia.stocks.model.StockEntity;
import com.yuejia.stocks.model.StorageFileNotFoundException;
import com.yuejia.stocks.service.StockService;
import com.yuejia.stocks.service.StorageService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StockController {

	private final StorageService storageService;
	private final StockService stockService;

	@Autowired
	public StockController(StorageService storageService, StockService stockService) {
		this.storageService = storageService;
		this.stockService = stockService;
	}


	/**
	 * HTML
	 */

	@GetMapping("/")
	public String loadHomePage(@RequestParam(required = false) String stock, Model model) {
		model.addAttribute("newStock", new StockEntity());

		//Load imported file list
		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder.fromMethodName(StockController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		//Load imported stock list
		if (Strings.isBlank(stock))
			model.addAttribute("records", stockService.findAll());
		else
			model.addAttribute("records", stockService.findAllByStock(stock));

		return "index";
	}

	@PostMapping("/uploadFile")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		storageService.store(file);
		stockService.bulkSave(file);

		redirectAttributes.addFlashAttribute("message",
				"Successfully uploaded " + file.getOriginalFilename());

		return "redirect:/";
	}

	@PostMapping("/add")
	public String addSubmit(@ModelAttribute StockEntity newStock, Model model, RedirectAttributes redirectAttributes) {
		//TODO: invalid/duplicate check
		stockService.singleSave(newStock);
		redirectAttributes.addFlashAttribute("message",
				"Successfully added " + newStock.getStock());

		return "redirect:/";
	}

	@PostMapping("/filter")
	public String filterSubmit(@RequestParam String stock) {
		return "redirect:/?stock=" + stock;
	}


	/**
	 * REST
	 */

	@GetMapping("/api/{stock}")
	public ResponseEntity<List<StockEntity>> getStocks(@PathVariable(required = false) String stock) {
		List<StockEntity> matchStocks;

		//TODO: input validation
		if (Strings.isBlank(stock))
			matchStocks = stockService.findAll();
		else
			matchStocks = stockService.findAllByStock(stock);

		return ResponseEntity.ofNullable(matchStocks).ok().body(matchStocks);
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		var file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
