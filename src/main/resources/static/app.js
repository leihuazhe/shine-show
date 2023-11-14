function preview_image(F, image_container) {
    let my_container = document.querySelector(image_container);

    let new_container = document.createElement('div');
    new_container.className = 'image_preview_container';

    F = F.files;
    F = Array.from(F);

    F.forEach(file => {
        let my_url = URL.createObjectURL(file);

        let my_image = getImage(my_url);
        new_container.append(my_image);
    });

    my_container.replaceWith(new_container);
}

function getImage(url) {
    let img = document.createElement('img');
    img.src = url;
    img.alt = "Image Not Found";

    return img;
}