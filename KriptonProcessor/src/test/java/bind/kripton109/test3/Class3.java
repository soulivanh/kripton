package bind.kripton109.test3;

import java.util.Date;

import com.abubusoft.kripton.annotation.BindType;
import com.abubusoft.kripton.annotation.BindTypeVariables;

@BindType
@BindTypeVariables(value={"A","B", "C"}, typeParameters={Integer.class, Date.class, String.class})
public class Class3 extends Class2<Integer, Date> {

}
