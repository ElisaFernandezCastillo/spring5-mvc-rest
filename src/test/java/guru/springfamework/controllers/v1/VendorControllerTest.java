package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
    }

    @Test
    public void testListVendors() throws Exception {
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1L);
        vendor1.setName("Western Tasty Fruits Ltd.");
        vendor1.setVendorUrl("/api/v1/vendors/1");

        VendorDTO vendor2 = new VendorDTO();
        vendor1.setId(2L);
        vendor1.setName("Exotic Fruits Company");
        vendor1.setVendorUrl("/api/v1/vendors/2");

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1,vendor2));

        mockMvc.perform(get("/api/v1/vendors")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorById() throws Exception {
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1L);
        vendor1.setName("Western Tasty Fruits Ltd.");
        vendor1.setVendorUrl("/api/v1/vendors/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        mockMvc.perform(get("/api/v1/vendors/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Western Tasty Fruits Ltd.")));

    }

    @Test
    public void createNewVendor() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("Western Tasty Fruits Ltd.");

        VendorDTO returnDto = new VendorDTO();
        returnDto.setId(1L);
        returnDto.setName(vendor1.getName());
        returnDto.setVendorUrl("/api/v1/vendors/1");

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnDto);

        mockMvc.perform(post("/api/v1/vendors")
                .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(vendor1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(returnDto.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(returnDto.getVendorUrl())));
    }

    @Test
    public void deleteVendorById () throws Exception {
        mockMvc.perform(delete("/api/v1/vendors/1")
                .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());

    }

    @Test
    public void testPatchCustomer() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Fruits 1");

        VendorDTO returnDto = new VendorDTO();
        returnDto.setName("Generic Fruits Company");
        returnDto.setVendorUrl("/api/v1/vendors/1");

        when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch("/api/v1/vendors/1")
                .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(returnDto.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }
}