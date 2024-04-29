for i in {1..3000}
do
  echo "Round $i"
#  sleep 0.1
  curl http://localhost:9090/owners > /dev/null &
#  sleep 0.1
  curl http://localhost:9090/owners/1 > /dev/null &
#  sleep 0.1
  curl http://localhost:9090/owners/2 > /dev/null &
#  sleep 0.1
  curl http://localhost:9090/owners/3 > /dev/null &
#  sleep 0.1
  curl http://localhost:9090/owners/4 > /dev/null &
#  sleep 0.1
  curl http://localhost:9090/owners/5 > /dev/null &
done

wait
