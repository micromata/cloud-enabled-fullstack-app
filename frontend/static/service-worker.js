'use strict';

/* eslint-env browser, serviceworker */

self.addEventListener('install', () => {
    self.skipWaiting();
});


self.onpush = async function (event) {
    const data = event.data.json();

    let notificationTitle = data.title;
    const notificationOptions = {
        body: data.description
    };

    event.waitUntil(
        self.registration.showNotification(
            notificationTitle,
            notificationOptions,
        ),
    );
}

self.addEventListener('notificationclick', function(event) {
    console.log('Notification clicked.');
    event.notification.close();

    let clickResponsePromise = Promise.resolve();
    if (event.notification.data && event.notification.data.url) {
        clickResponsePromise = clients.openWindow(event.notification.data.url);
    }

    event.waitUntil(clickResponsePromise);
});