package testing;

import com.example.ispw2.bean.FiltriBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.model.Filtri;
import org.junit.jupiter.api.Test;

public class GetFiltriTest {
    @Test
    public void getFiltritest() {
        FiltriBean filtriBean = HomeClienteController.getInstance().getFiltri();
        System.out.println(filtriBean.toString());
    }

}
