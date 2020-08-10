#University appointment api

##API:
основной аддрес localhost:8080

**Создание пользователя**
POST /create/user 
Возвращает строку: "User has been created"

![GitHub Logo](/images/)
Format: ![Alt Text](url)

**Получить список учителей**
GET /teachers
Возвращает имена всех учителей из бд.

![GitHub Logo](/images/)
Format: ![Alt Text](url)

Тело запроса

** Получить время учителя когда он доступен **
GET teacher/schedule

![GitHub Logo](/images/)
Format: ![Alt Text](url)

** Добавить график учителя **
POST teacher/schedule

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











