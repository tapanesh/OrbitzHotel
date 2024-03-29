/*
 * @author Satheesh.C.K
 * @project hotels
 * Date Created:22 Jan 2014
 * 
 * */

package com.ibs.hotels.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibs.hotels.form.Hotel;
import com.ibs.hotels.form.SearchMenu;
import com.ibs.hotels.form.SearchMenuValidator;
import com.ibs.hotels.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchMenuValidator searchValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(searchValidator);
	}

	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	public ModelAndView showContacts() {

		return new ModelAndView("searchHome", "newSearch", new SearchMenu());
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView listHotels(
			@ModelAttribute("newSearch") @Valid SearchMenu srch,
			BindingResult result) {
		// SearchMenu srch = (SearchMenu) model;
		if (result.hasErrors()) {
			return new ModelAndView("searchHome", "newSearch", srch);
		} else {
			List<Hotel> htlLst = new ArrayList<Hotel>();
			Map<String, Object> map = new HashMap<String, Object>();
			System.out.println(srch);
			htlLst = searchService.listHotels(srch.getLocation(),
					srch.getCheckIn(), srch.getCheckOut(), srch.getNoOfRooms());
			//map.put("newSearch", new SearchMenu());
			map.put("newSearch", srch);
			map.put("hotelList", htlLst);

			return new ModelAndView("searchResult", map);
		}
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

}
