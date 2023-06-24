package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorServiceImpl vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
    }
    @Test
    public void getAllVendors() throws Exception {
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Western Tasty Fruits");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Exotic Fruits Company");

        when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(2,vendorDTOS.size());
    }

    @Test
    public void getVendorById() throws Exception {
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Western Tasty Fruits");


        when(vendorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(vendor1));

        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        assertEquals("Western Tasty Fruits",vendorDTO.getName());

    }

    @Test
    public void createNewCustomer() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("Franks Fresh Fruits from France Ltd.");

        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setId(vendorDTO.getId());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnVendorDto = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), returnVendorDto.getName());
        assertEquals("/api/v1/vendors/"+vendorDTO.getId(),returnVendorDto.getVendorUrl());

    }

    @Test
    public void deleteVendorById() throws Exception {
        Long id = 1L;
        vendorRepository.deleteById(id);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testSaveCustomerByDTO() throws Exception {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Franks Fresh Fruits from France Ltd.");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO updatedVendor = vendorService.saveVendorByDTO(1L,vendorDTO);

        //then
        assertEquals(vendorDTO.getName(),updatedVendor.getName());
        assertEquals("/api/v1/vendors/1",updatedVendor.getVendorUrl());

    }

}