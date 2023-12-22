export function setup () {
    if ('serviceWorker' in navigator) {
        console.log("Loading service worker...")
        navigator.serviceWorker.register("/service-worker.js")
            .then(initializeState)
    } else {
        console.warn('Service workers are not supported in this browser.');
    }
}

function initializeState() {
    console.log("Loaded")
    if (!('showNotification' in ServiceWorkerRegistration.prototype)) {
        console.warn('Notifications aren\'t supported.');
        return;
    }

    if (Notification.permission === 'denied') {
        console.warn('The user has blocked notifications.');
        return;
    }

    if (!('PushManager' in window)) {
        console.warn('Push messaging isn\'t supported.');
        return;
    }

    if (Notification.permission === "granted") {
        subscribe();
    }
}

export function subscribe() {
    return new Promise((resolve, reject) => {
        navigator.serviceWorker.ready.then(function (serviceWorkerRegistration) {

            serviceWorkerRegistration.pushManager.subscribe({
                userVisibleOnly: true,
                applicationServerKey: "BIhDSNQxHDSqLgy5oRod-0AVs0HuvsxzubgtZ26HUweOFwT0AnAPd7v9mexrN3RpnETdJyw4w3pXegd_J3wCBco"
            }).then(function (subscription) {
                const key = subscription.getKey ? subscription.getKey('p256dh') : null;
                const auth = subscription.getKey ? subscription.getKey('auth') : null;
                const endpoint = subscription.endpoint;

                resolve({
                    key: key ? btoa(String.fromCharCode.apply(null, new Uint8Array(key))) : '',
                    auth: auth ? btoa(String.fromCharCode.apply(null, new Uint8Array(auth))) : '',
                    endpoint
                })
            })
                .catch(function (e) {
                    if (Notification.permission === 'denied') {
                        console.warn('Permission for Notifications was denied');
                    } else {
                        console.error('Unable to subscribe to push.', e);
                    }
                    reject(e);
                });
        });
    });

}
