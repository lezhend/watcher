import com.alibaba.fastjson.JSON;
import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import com.fortinet.fcasb.watcher.alert.service.ESService;
import org.junit.Test;

import java.util.Date;

/**
 * Created by zliu on 17/3/16.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {CommonConfig.class})
public class TestESService {

//    @Autowired
    private ESService esService;

    @Test
    public void testSearch(){
        MonitorMetric mm = new MonitorMetric();
        mm.setName("test");
        mm.setType(MonitorTypeEnum.ES);
        mm.setCreateTime(new Date().toString());
        mm.setUpdateTime(new Date().toString());
        System.out.println(JSON.toJSONString(mm));
        System.out.println(JSON.parseObject(JSON.toJSONString(mm),MonitorMetric.class).getType());
    }


}
