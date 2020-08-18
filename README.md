# University appointment api

## API:
основной аддрес localhost:8080

###__Создание пользователя__

POST /user 
Возвращает строку: "User has been created"

Необходимые параметры тела запроса:
* userType - тип пользователя (student или teacher)
* userName - имя пользователя, должно быть уникальным
* userEmail - почта пользователя, должно быть уникальным

![](/src/main/resources/images/create_user.png)

###__Получить список учителей__

GET /teachers
Возвращает имена всех учителей из бд.

![](/src/main/resources/images/teachers)

###__Получить время учителя когда он доступен__

GET teacher/schedule

Необходимы параметр запроса:
* teacherName - имя преподователя

![](/src/main/resources/images/get_teacher_schedule.png)

###__Добавить график учителя__

POST teacher/schedule

Необходимые параметры тела запроса:
* teacherName - имя преподователя
* appointmentDate - дата начала занятия (формат: год-месяц-день час-минуты)
* appointmentFinishDate - дата окончания занятия (формат: год-месяц-день час-минуты)

![](/src/main/resources/images/teacher_schedule)

###__Создать резервацию студенту__

POST /reservation

![](/images/)

###__Отменить резервацию студенту__

DELETE /reservation

![](/images/)

###__Принять резервацию учителю__

POST /reservation/apply

![](/images/)

###__Отменить резервацию учителю__

DELETE /reservation/decline

![](/images/)

###__Создать стоимость за определенное кол-во времени__

POST /price_rate

Необходимые параметры тела запроса:
* teacherName - имя преподователя
* time - время
* price - целочисленная стоимость за указанное время

![](/src/main/resources/images/price_rate.png)











