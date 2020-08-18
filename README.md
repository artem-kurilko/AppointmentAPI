# University appointment api

## API:
основной аддрес localhost:8080

__Создание пользователя__
POST /user 
Возвращает строку: "User has been created"

Необходимые параметры тела запроса:
* userType - тип пользователя (student или teacher)
* userName - имя пользователя, должно быть уникальным
* userEmail - почта пользователя, должно быть уникальным

![](/src/main/resources/images/create_user.png)

__Получить список учителей__
GET /teachers
Возвращает имена всех учителей из бд.

![](/src/main/resources/images/teachers)

__ Получить время учителя когда он доступен __
GET teacher/schedule

Необходимы параметр запроса:
* teacherName - имя преподователя

![](/src/main/resources/images/get_teacher_schedule.png)

__ Добавить график учителя __
POST teacher/schedule

Необходимые параметры тела запроса:
* 

![](/src/main/resources/images/teacher_schedule)

__ Создать резервацию студенту __
POST /reservation

![](/images/)

__ Отменить резервацию студенту __
DELETE /reservation

![](/images/)

__ Принять резервацию учителю __
POST /reservation/apply

![](/images/)

__ Отменить резервацию учителю __
DELETE /reservation/decline

![](/images/)

__ Создать стоимость за определенное кол-во времени __
POST /price_rate

Необходимые параметры тела запроса:
* teacherName - имя преподователя
* time - время 
* price - целочисленная стоимость за указанное время

![](/src/main/resources/images/price_rate.png)











