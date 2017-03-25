import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.service.ESService;
import org.elasticsearch.search.SearchHits;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zliu on 17/3/16.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {CommonConfig.class})
public class TestESService {

//    @Autowired
    private ESService esService;

//    @Test
    public void testSearch(){
        Alert alert = new Alert();
        alert.setSearchkey("CPU*");
        Map<String,String> filter = new HashMap<>();
        filter.put("filter","dlp");
        alert.setField("value");
        alert.setConditionvalue("40");
        alert.setIndex("logstash-monitor-statistics-log-2017.03.25");
        try {
           SearchHits searchHits = esService.search(alert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
