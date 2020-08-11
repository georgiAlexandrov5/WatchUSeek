package softuni.WatchUSeek.services.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.WatchUSeek.data.entities.Strap;
import softuni.WatchUSeek.data.models.service.StrapServiceModel;
import softuni.WatchUSeek.data.models.service.WatchServiceModel;
import softuni.WatchUSeek.errors.StrapNotFoundException;
import softuni.WatchUSeek.repositories.StrapRepository;
import softuni.WatchUSeek.service.StrapService;
import softuni.WatchUSeek.services.base.ServiceTestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StrapServiceTest extends ServiceTestBase {


    private String expected;
    private String actual;
    List<Strap> straps;
    StrapServiceModel strapServiceModel;
    private final ModelMapper modelMapper;


    @MockBean
    StrapRepository strapRepository;


    @Autowired
    StrapService strapService;

    @Autowired
    public StrapServiceTest(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    @BeforeEach
    protected void beforeEach() {
        Strap strap = new Strap();

        straps = new ArrayList<>();
        Mockito.when(strapRepository.findAll()).thenReturn(straps);

        strapServiceModel = new StrapServiceModel();
        strapServiceModel.setPicUrl("https://storage.grand-seiko.com/production-b/uploads/2019/04/gs_site_banner_pc_1920_600_26023449567264_jpg.jpg");
        strapServiceModel.setPhone("88888888888");
        strapServiceModel.setDescription("Epic Strap");
        strapServiceModel.setMaterial("Leather");
        strapServiceModel.setPrice(1200.00);
        strapServiceModel.setName("Cordoba");



    }

    @Test
    void findAllStraps_ShouldReturnCorrect() {
        Strap strap1 = new Strap();
        Strap strap2 = new Strap();

        strap1.setId("1");
        strap2.setId("2");
        straps.add(strap1);
        straps.add(strap2);

        List<StrapServiceModel> strapServiceModelList = strapService.findAll();


        Assert.assertEquals(straps.size(), strapServiceModelList.size());
        Assert.assertEquals(straps.get(0).getId(), strapServiceModelList.get(0).getId());
        Assert.assertEquals(straps.get(1).getId(), strapServiceModelList.get(1).getId());
    }

    @Test
    void findAllStraps_whenEmpty_shouldReturnEmpty() {
        straps.clear();

        List<StrapServiceModel> straps = strapService.findAll();

        Assert.assertEquals(0, straps.size());
    }

    @Test
    void findStrapById_whenInvalidId_shouldThrow() {
        String id = "2";
        Mockito.when(this.strapRepository.findById("1"))
                .thenReturn(Optional.empty());
        assertThrows(
                StrapNotFoundException.class,
                () -> this.strapService.findStrapById(id));

    }

    @Test
    void getWatchById_whenInvalidId_shouldThrow() {
        String id = "2";
        Mockito.when(this.strapRepository.findById("1"))
                .thenReturn(Optional.empty());
        assertThrows(
                StrapNotFoundException.class,
                () -> this.strapService.findStrapById(id));

    }

    @Test
    void addStrap_whenValidInput_shouldAdd() {
        straps.clear();


        Strap strap = this.modelMapper.map(strapServiceModel, Strap.class);
        Mockito.when(strapRepository.saveAndFlush(strap)).thenReturn(strap);

        straps.add(strap);

        assertNotNull(strapRepository.findAll().size());
        assertEquals(straps.get(0).getMaterial(), strapServiceModel.getMaterial());
        assertEquals(straps.get(0).getName(), strapServiceModel.getName());

    }

    @Test
    void findStrapByName_WhenInvalid_ShouldThrow(){
        String name = "Name";
        this.strapService.addStrap(strapServiceModel);
        Mockito.when(this.strapRepository.findByName(name))
                .thenReturn(Optional.empty());
        assertThrows(
                StrapNotFoundException.class,
                () -> this.strapService.findStrapByName(name));
    }



    @Test
    void delete_whenExist_shouldDelete(){

        String id="1";
        Strap strap=new Strap();
        strap.setId(id);
        Mockito.when(strapRepository.findById(id)).thenReturn(Optional.of(strap)).thenThrow(RuntimeException.class);
        this.strapService.deleteStrap(id);
        assertThrows(RuntimeException.class,
                ()->this.strapService.deleteStrap(id));
    }
















}
