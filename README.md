# University appointment api

Для работы с приложением откройте программу в вашей среде разработки и запустите класс AppointmentRunner.

В классе EmailNotification в методе sendMail, замените значения для SENDER_NAME и SENDER_PASSWORD, указав вашу почту и пароль к ней.

Установите (https://www.postman.com/downloads/) и откройте postman.


## API:
Базовый аддрес: localhost:8080

### Создание пользователя

POST /user 
Возвращает строку: "User has been created"

Необходимые параметры тела запроса:
* userType - тип пользователя (student или teacher)
* userName - имя пользователя, должно быть уникальным
* userEmail - почта пользователя, должно быть уникальным

Если пользователь создастся вы увидите надпись "User has been created" и если вы правильно указали почту, вам на неё придёт сообщение о том что пользователь создан.

![](/src/main/resources/images/create_user.png)

### Получить список учителей

GET /teachers
Возвращает имена всех учителей из бд.

![](/src/main/resources/images/teachers.png)

### Получить время учителя когда он доступен

GET teacher/schedule

Необходимый параметр запроса:
* teacherName - имя преподователя

![](/src/main/resources/images/get_teacher_schedule.png)

### Добавить график учителя

POST teacher/schedule

Необходимые параметры тела запроса:
* teacherName - имя преподователя
* appointmentDate - дата начала занятия (формат: год-месяц-день час-минуты)
* appointmentFinishDate - дата окончания занятия (формат: год-месяц-день час-минуты)

![](/src/main/resources/images/teacher_schedule.png)

### Создать резервацию студенту

POST /reservation

Необходимые параметры тела запроса:
* studentName - имя студента
* appointmentDate - дата начала занятия (формат: год-месяц-день час-минуты)
* appointmentFinishDate - дата окончания занятия (формат: год-месяц-день час-минуты)
* teacherName - имя преподователя

![](/src/main/resources/images/reservation.png)

### Отменить резервацию студенту

DELETE /reservation

Необходимые параметры тела запроса:
* studentName - имя студента
* appointmentDate - дата начала занятия (формат: год-месяц-день час-минуты)
* appointmentFinishDate - дата окончания занятия (формат: год-месяц-день час-минуты)
* teacherName - имя преподователя

![](/src/main/resources/images/cancel.png)

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











