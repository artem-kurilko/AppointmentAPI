# University appointment api

## API:
основной аддрес localhost:8080

### Создание пользователя

POST /user 
Возвращает строку: "User has been created"

Необходимые параметры тела запроса:
* userType - тип пользователя (student или teacher)
* userName - имя пользователя, должно быть уникальным
* userEmail - почта пользователя, должно быть уникальным

![](/src/main/resources/images/create_user.png)

### Получить список учителей

GET /teachers
Возвращает имена всех учителей из бд.

![](/src/main/resources/images/teachers)

### Получить время учителя когда он доступен

GET teacher/schedule

Необходимы параметр запроса:
* teacherName - имя преподователя

![](/src/main/resources/images/get_teacher_schedule.png)

### Добавить график учителя

POST teacher/schedule

Необходимые параметры тела запроса:
* teacherName - имя преподователя
* appointmentDate - дата начала занятия (формат: год-месяц-день час-минуты)
* appointmentFinishDate - дата окончания занятия (формат: год-месяц-день час-минуты)

![](/src/main/resources/images/teacher_schedule)

### Создать резервацию студенту

POST /reservation

![](/images/)

### Отменить резервацию студенту

DELETE /reservation

![](/images/)

### Принять резервацию учителю

POST /reservation/apply

![](/images/)

### Отменить резервацию учителю

DELETE /reservation/decline

![](/images/)

### Создать стоимость за определенное кол-во времени

POST /price_rate

Необходимые параметры тела запроса:
* teacherName - имя преподователя
* time - время
* price - целочисленная стоимость за указанное время

![](/src/main/resources/images/price_rate.png)











