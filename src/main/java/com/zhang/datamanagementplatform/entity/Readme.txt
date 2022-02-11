Java各种对象（PO,BO,VO,DTO,POJO,DAO,Entity,JavaBean,JavaBeans）的区分

PO：持久对象 (persistent object)，po(persistent object)就是在Object/Relation Mapping框架中的Entity，po的每个属性基本上都对应数据库表里面的某个字段。
    完全是一个符合Java Bean规范的纯Java对象，没有增加别的属性和方法。持久对象是由insert数据库创建，由数据库delete删除的。基本上持久对象生命周期和数据库密切相关。

VO：值对象(Value Object)，通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。但应是抽象出的业务对象，可以和表对应，也可以不，这根据业务的需要。
    表现层对象(View Object)，主要对应展示界面显示的数据对象，用一个VO对象来封装整个界面展示所需要的对象数据。

BO：业务对象层的缩写(Business Object)，封装业务逻辑的java对象，通过调用DAO方法，结合PO,VO进行业务操作。

DTO：数据传输对象(Data Transfer Object)，是一种设计模式之间传输数据的软件应用系统。数据传输目标往往是数据访问对象从数据库中检索数据。
     数据传输对象与数据交互对象或数据访问对象之间的差异是一个以不具有任何行为除了存储和检索的数据（访问和存取器）。简单来说，当我们需要一个对象10个字段的内容，
     但这个对象总共有20个字段，我们不需要把整个PO对象全部字段传输到客户端，而是可以用DTO重新封装，传递到客户端。此时，如果这个对象用来对应界面的展现，就叫VO。

POJO：POJO（Plain Ordinary Java Object）简单的Java对象，实际就是普通JavaBeans，是为了避免和EJB混淆所创造的简称。
      通指没有使用Entity Beans的普通java对象，可以把POJO作为支持业务逻辑的协助类。

