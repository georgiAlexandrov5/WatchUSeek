package softuni.WatchUSeek.services.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.WatchUSeek.data.entities.Watch;
import softuni.WatchUSeek.data.models.service.WatchServiceModel;
import softuni.WatchUSeek.errors.WatchNotFoundException;
import softuni.WatchUSeek.repositories.WatchRepository;
import softuni.WatchUSeek.service.interfaces.WatchService;
import softuni.WatchUSeek.services.base.ServiceTestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class WatchServiceTest extends ServiceTestBase {

    List<Watch> watches;
    @MockBean
    WatchRepository watchRepository;


    private String expected;
    private String actual;
    WatchServiceModel watchServiceModel;
    private final ModelMapper modelMapper;


    @Autowired
    WatchService watchService;

    @Autowired
    public WatchServiceTest(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @BeforeEach
    protected void beforeEach() {
        Watch watch = new Watch();

        watches = new ArrayList<>();
        Mockito.when(watchRepository.findAll()).thenReturn(watches);

        watchServiceModel = new WatchServiceModel();
        watchServiceModel.setPicUrl("https://storage.grand-seiko.com/production-b/uploads/2019/04/gs_site_banner_pc_1920_600_26023449567264_jpg.jpg");
        watchServiceModel.setContactNumber("88888888888");
        watchServiceModel.setDescription("Epic Watch");
        watchServiceModel.setGender("FEMALE");
        watchServiceModel.setPrice(1200.00);
        watchServiceModel.setManufacturer("Seiko");
        watchServiceModel.setRefNumber("7S26");
        watchServiceModel.setYear(2000);
        watchServiceModel.setId("1");


    }


    @Test
    void findAllWatches_ShouldReturnCorrect() {
        Watch watch1 = new Watch();
        Watch watch2 = new Watch();
        watch1.setId("1");
        watch2.setId("2");
        watches.add(watch1);
        watches.add(watch2);

        List<WatchServiceModel> watchesServiceModelList = watchService.findAll();


        Assert.assertEquals(watches.size(), watchesServiceModelList.size());
        Assert.assertEquals(watches.get(0).getId(), watchesServiceModelList.get(0).getId());
        Assert.assertEquals(watches.get(1).getId(), watchesServiceModelList.get(1).getId());
    }


    @Test
    void findAllWatches_whenEmpty_shouldReturnEmpty() {
        watches.clear();

        List<WatchServiceModel> watches = watchService.findAll();

        Assert.assertEquals(0, watches.size());
    }



    @Test
    void getWatchById_whenInvalidId_shouldThrow() {
        String id = "2";
        Mockito.when(this.watchRepository.findById("1"))
                .thenReturn(Optional.empty());
        assertThrows(
                WatchNotFoundException.class,
                () -> this.watchService.findWatchById(id));

    }

    @Test
    void addWatch_whenValidInput_shouldAdd() {
        watches.clear();


        Watch watch = this.modelMapper.map(watchServiceModel, Watch.class);
        Mockito.when(watchRepository.saveAndFlush(watch)).thenReturn(watch);

        watches.add(watch);

        assertNotNull(watchRepository.findAll().size());
        assertEquals(watches.get(0).getManufacturer(), watchServiceModel.getManufacturer());
        assertEquals(watches.get(0).getDescription(), watchServiceModel.getDescription());

    }


    //TODO
    @Test
    void findWatchById_WithValid() {
        String id = "1";
        Watch watch = this.modelMapper.map(watchServiceModel, Watch.class);
        watch.setId(id);

        this.watchRepository.save(watch);

        Mockito.when(this.watchRepository.findById(watch.getId()))
                .thenReturn(Optional.of(watch));
        assertEquals(watchServiceModel.getId(), watchService.findWatchById(id).getId());
    }

    @Test
    void findWatchByRef_WhenInvalid_ShouldThrow(){
        String refNumber = "Ref";
        Mockito.when(this.watchRepository.findByRefNumber("Ref"))
                .thenReturn(Optional.empty());
        assertThrows(
                WatchNotFoundException.class,
                () -> this.watchService.findWatchByRefNumber(refNumber));
    }

    @Test
    void findWatchById_whenInvalidId_shouldThrowException() {
        String id = "2";
        Mockito.when(this.watchRepository.findById("2"))
                .thenReturn(Optional.empty());
        assertThrows(
                WatchNotFoundException.class,
                () -> this.watchService.findWatchById(id));

    }

    @Test
    void delete_whenExist_shouldDelete(){

        String id="1";
        Watch watch=new Watch();
        watch.setId(id);
        Mockito.when(watchRepository.findById(id)).thenReturn(Optional.of(watch)).thenThrow(RuntimeException.class);
        this.watchService.deleteWatch(id);
        assertThrows(RuntimeException.class,
                ()->this.watchService.deleteWatch(id));
    }














}
