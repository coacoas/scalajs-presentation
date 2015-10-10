function triangle(canvas) {
    canvas.height = 255
    canvas.width = 255
    var ctx = canvas.getContext("2d")
    var count = 0
    var p = {x: 0, y: 0}

    var corners = [{x: 255, y: 255}, {x: 0, y: 255}, {x: 128, 0}]

    function clear() {
        ctx.fillStyle = "black"
        ctx.fillRect(0, 0, 255, 255)
    }

    function randomInt(max) {
        return Math.floor((Math.random() * max) + 1);
    }

    function run () {
        for (var i = 0; i < 10; i++) {
            if (count % 3000 == 0) clear()
            count += 1
            p = (p + corners(Random.nextInt(3))) / 2

        }

        val height = 512.0 / (255 + p.y)
        val r = (p.x * height).toInt
        val g = ((255-p.x) * height).toInt
        val b = p.y
        ctx.fillStyle = s"rgb($g, $r, $b)"

        ctx.fillRect(p.x, p.y, 1, 1)
    }

    dom.setInterval(() => run, 50)
}
