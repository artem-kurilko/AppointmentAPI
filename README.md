#University appointment api

##API:
основной аддрес localhost:8080

**Создание пользователя**
POST /user 
Возвращает строку: "User has been created"

Необходимые параметры тела запроса:
* userType - тип пользователя (student или teacher)
* userName - имя пользователя, должно быть уникальным
* userEmail - почта пользователя, должно быть уникальным

![GitHub Logo](/images/)
Format: ![Alt Text](url)

**Получить список учителей**
GET /teachers
Возвращает имена всех учителей из бд.

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Получить время учителя когда он доступен **
GET teacher/schedule

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Добавить график учителя **
POST teacher/schedule

Необходимые параметры тела запроса:
* 

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Создать резервацию студенту **
POST /reservation

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Отменить резервацию студенту **
DELETE /reservation

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Принять резервацию учителю **
POST /reservation/apply

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Отменить резервацию учителю **
DELETE /reservation/decline

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Создать стоимость за определенное кол-во времени **
POST /price_rate

![GitHub Logo](/images/)
Format: ![Alt Text](url)











